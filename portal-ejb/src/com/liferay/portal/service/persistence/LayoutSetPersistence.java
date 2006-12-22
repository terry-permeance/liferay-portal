/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="LayoutSetPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutSetPersistence extends BasePersistence {
	public LayoutSet create(String ownerId) {
		LayoutSet layoutSet = new LayoutSetImpl();
		layoutSet.setNew(true);
		layoutSet.setPrimaryKey(ownerId);

		return layoutSet;
	}

	public LayoutSet remove(String ownerId)
		throws NoSuchLayoutSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSet layoutSet = (LayoutSet)session.get(LayoutSetImpl.class,
					ownerId);

			if (layoutSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No LayoutSet exists with the primary key " +
						ownerId);
				}

				throw new NoSuchLayoutSetException(
					"No LayoutSet exists with the primary key " + ownerId);
			}

			return remove(layoutSet);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet remove(LayoutSet layoutSet) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(layoutSet);
			session.flush();

			return layoutSet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet) throws SystemException {
		return update(layoutSet, false);
	}

	public com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(layoutSet);
			}
			else {
				if (layoutSet.isNew()) {
					session.save(layoutSet);
				}
			}

			session.flush();
			layoutSet.setNew(false);

			return layoutSet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet findByPrimaryKey(String ownerId)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByPrimaryKey(ownerId);

		if (layoutSet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No LayoutSet exists with the primary key " +
					ownerId);
			}

			throw new NoSuchLayoutSetException(
				"No LayoutSet exists with the primary key " + ownerId);
		}

		return layoutSet;
	}

	public LayoutSet fetchByPrimaryKey(String ownerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (LayoutSet)session.get(LayoutSetImpl.class, ownerId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet findByC_V(String companyId, String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByC_V(companyId, virtualHost);

		if (layoutSet == null) {
			String msg = "No LayoutSet exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "virtualHost=";
			msg += virtualHost;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchLayoutSetException(msg);
		}

		return layoutSet;
	}

	public LayoutSet fetchByC_V(String companyId, String virtualHost)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (virtualHost == null) {
				query.append("virtualHost IS NULL");
			}
			else {
				query.append("virtualHost = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (virtualHost != null) {
				q.setString(queryPos++, virtualHost);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			LayoutSet layoutSet = (LayoutSet)list.get(0);

			return layoutSet;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.LayoutSet ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_V(String companyId, String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByC_V(companyId, virtualHost);
		remove(layoutSet);
	}

	public void removeAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("DELETE ");
			query.append("FROM com.liferay.portal.model.LayoutSet");

			Query q = session.createQuery(query.toString());
			q.executeUpdate();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_V(String companyId, String virtualHost)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (virtualHost == null) {
				query.append("virtualHost IS NULL");
			}
			else {
				query.append("virtualHost = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (virtualHost != null) {
				q.setString(queryPos++, virtualHost);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.LayoutSet");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(LayoutSetPersistence.class);
}