/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.beangle.ems.log.service;

import java.util.Date;

import org.beangle.commons.context.event.BusinessEvent;
import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventListener;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.ems.log.model.BusinessLogDetailBean;
import org.beangle.security.Securities;
import org.beangle.security.core.Authentication;
import org.beangle.security.web.auth.WebAuthenticationDetails;

/**
 * @author chaostone
 * @version $Id: BusinessEventLogger.java Jun 29, 2011 9:28:33 A M chaostone $
 */
public class BusinessEventLogger extends BaseServiceImpl implements EventListener<Event> {

  public void onEvent(Event event) {
    Authentication auth = Securities.getAuthentication();
    if (null == auth) return;
    BusinessLogBean log = new BusinessLogBean();
    log.setOperateAt(new Date(event.getTimestamp()));
    log.setOperation(Strings.defaultIfBlank(event.getSubject(), "  "));
    log.setResource(Strings.defaultIfBlank(event.getResource(), "  "));
    log.setOperator(auth.getName());
    Object details = auth.getDetails();
    if ((details instanceof WebAuthenticationDetails)) {
      WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
      log.setIp(webDetails.getAgent().getIp());
      log.setAgent(webDetails.getAgent().getOs() + " " + webDetails.getAgent().getBrowser());
      log.setEntry(Strings.defaultIfBlank(webDetails.getLastAccessUri(), "--"));
    }
    if (null != event.getDetail()) {
      log.setDetail(new BusinessLogDetailBean(log, event.getDetail()));
    }
    entityDao.saveOrUpdate(log);
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return BusinessEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
