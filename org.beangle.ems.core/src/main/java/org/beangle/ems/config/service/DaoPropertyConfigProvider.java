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

package org.beangle.ems.config.service;

import java.util.List;
import java.util.Properties;

import org.beangle.commons.context.property.PropertyConfig;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.ems.config.model.PropertyConfigItemBean;

public class DaoPropertyConfigProvider implements PropertyConfig.Provider {

  private EntityDao entityDao;

  public void setEntityDao(EntityDao entityDao) throws ClassNotFoundException {
    this.entityDao = entityDao;
  }

  public Properties getConfig() {
    Properties props = new Properties();
    List<PropertyConfigItemBean> rs = entityDao.getAll(PropertyConfigItemBean.class);
    for (final PropertyConfigItemBean prop : rs) {
      Class<?> itemClass = null;
      try {
        itemClass = Class.forName(prop.getType());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      Object value = prop.getValue();
      if (null != itemClass && Entity.class.isAssignableFrom(itemClass)) {
        value = entityDao.get(itemClass, Long.valueOf(value.toString()));
      }
      props.put(prop.getName(), value);
    }
    return props;
  }
}
