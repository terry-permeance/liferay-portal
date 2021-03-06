AUI.add(
	'liferay-ddl-form-builder-layout-deserializer',
	function(A) {
		var Lang = A.Lang;

		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;
		var RendererUtil = Liferay.DDM.Renderer.Util;

		var LayoutDeserializer = A.Component.create(
			{
				ATTRS: {
					builder: {
					},

					definition: {
						validator: Lang.isObject
					},

					descriptions: {
						validator: Array.isArray,
						value: []
					},

					pages: {
						validator: Array.isArray,
						value: []
					},

					titles: {
						validator: Array.isArray,
						value: []
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-layout-deserializer',

				prototype: {
					deserialize: function() {
						var instance = this;

						var pages = instance.get('pages');

						return instance._deserializePages(pages);
					},

					_deserializeColumn: function(column) {
						var instance = this;

						var deserializedColumn = new A.LayoutCol(
							{
								size: column.size
							}
						);

						if (column.fieldNames && column.fieldNames.length > 0) {
							var fieldsList = new Liferay.DDL.FormBuilderFieldList(
								{
									fields: instance._deserializeFields(deserializedColumn, column.fieldNames)
								}
							);

							deserializedColumn.set('value', fieldsList);
						}

						return deserializedColumn;
					},

					_deserializeColumns: function(columns) {
						var instance = this;

						return columns.map(A.bind('_deserializeColumn', instance));
					},

					_deserializeField: function(deserializedColumn, fieldName) {
						var instance = this;

						var builder = instance.get('builder');

						var definition = instance.get('definition');

						var defaultLanguageId = definition.defaultLanguageId;

						var context = RendererUtil.getFieldByKey(definition, fieldName);

						A.each(
							context,
							function(value, key) {
								if (key === 'options') {
									context[key] = value.map(
										function(option) {
											return {
												label: option.label[defaultLanguageId],
												value: option.value
											};
										}
									);
								}
								else if (Lang.isObject(value) && value[defaultLanguageId] !== undefined) {
									context[key] = value[defaultLanguageId];
								}
							}
						);

						instance._deserializeFieldRules(context);

						context.fieldName = context.name;
						context.portletNamespace = builder.get('portletNamespace');
						context.readOnly = true;
						context.visible = true;
						context.value = '';

						delete context.name;

						var fieldClass = FormBuilderUtil.getFieldClass(context.type, context);

						return new fieldClass(
							A.merge(
								context,
								{
									context: context,
									evaluatorURL: builder.get('evaluatorURL'),
									getFieldNameSettingFormContextURL: builder.get('getFieldNameSettingFormContextURL'),
									getFieldTypeSettingFormContextURL: builder.get('getFieldTypeSettingFormContextURL'),
									parent: builder
								}
							)
						);
					},

					_deserializeFieldRules: function(context) {
						var instance = this;

						var rules = context.rules || [];

						rules.forEach(
							function(rule) {
								if (rule.type === 'VALIDATION') {
									context.validation = {
										errorMessage: rule.errorMessage,
										expression: rule.expression
									};
								}
								else if (rule.type === 'VISIBILITY') {
									context.visibilityExpression = rule.expression;
								}
							}
						);

						delete context.rules;
					},

					_deserializeFields: function(deserializedColumn, fieldNames) {
						var instance = this;

						return fieldNames.map(A.bind('_deserializeField', instance, deserializedColumn));
					},

					_deserializePage: function(page) {
						var instance = this;

						var definition = instance.get('definition');

						var languageId = definition.defaultLanguageId;

						var description = page.description && page.description[languageId];

						instance.get('descriptions').push(description);

						var title = page.title && page.title[languageId];

						instance.get('titles').push(title);

						return new A.Layout(
							{
								rows: instance._deserializeRows(page.rows)
							}
						);
					},

					_deserializePages: function(pages) {
						var instance = this;

						var deserializedPages;

						if (pages.length) {
							deserializedPages = pages.map(A.bind('_deserializePage', instance));
						}
						else {
							deserializedPages = [
								new A.Layout(
									{
										rows: [
											new A.LayoutRow()
										]
									}
								)
							];
						}

						return deserializedPages;
					},

					_deserializeRow: function(row) {
						var instance = this;

						return new A.LayoutRow(
							{
								cols: instance._deserializeColumns(row.columns)
							}
						);
					},

					_deserializeRows: function(rows) {
						var instance = this;

						return rows.map(A.bind('_deserializeRow', instance));
					}
				}
			}
		);

		Liferay.namespace('DDL').LayoutDeserializer = LayoutDeserializer;
	},
	'',
	{
		requires: ['aui-layout', 'liferay-ddl-form-builder-field', 'liferay-ddl-form-builder-field-list', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer-util']
	}
);