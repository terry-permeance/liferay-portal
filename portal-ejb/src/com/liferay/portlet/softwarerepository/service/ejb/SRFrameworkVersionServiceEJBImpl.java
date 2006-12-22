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

package com.liferay.portlet.softwarerepository.service.ejb;

import com.liferay.portal.service.impl.PrincipalSessionBean;

import com.liferay.portlet.softwarerepository.service.SRFrameworkVersionService;
import com.liferay.portlet.softwarerepository.service.SRFrameworkVersionServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SRFrameworkVersionServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionServiceEJBImpl
	implements SRFrameworkVersionService, SessionBean {
	public com.liferay.portlet.softwarerepository.model.SRFrameworkVersion addFrameworkVersion(
		java.lang.String plid, java.lang.String name, java.lang.String url,
		boolean active, int priority, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SRFrameworkVersionServiceFactory.getTxImpl().addFrameworkVersion(plid,
			name, url, active, priority, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.softwarerepository.model.SRFrameworkVersion addFrameworkVersion(
		java.lang.String plid, java.lang.String name, java.lang.String url,
		boolean active, int priority, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SRFrameworkVersionServiceFactory.getTxImpl().addFrameworkVersion(plid,
			name, url, active, priority, communityPermissions, guestPermissions);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);
		SRFrameworkVersionServiceFactory.getTxImpl().deleteFrameworkVersion(frameworkVersionId);
	}

	public com.liferay.portlet.softwarerepository.model.SRFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SRFrameworkVersionServiceFactory.getTxImpl().getFrameworkVersion(frameworkVersionId);
	}

	public com.liferay.portlet.softwarerepository.model.SRFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		PrincipalSessionBean.setThreadValues(_sc);

		return SRFrameworkVersionServiceFactory.getTxImpl()
											   .updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}