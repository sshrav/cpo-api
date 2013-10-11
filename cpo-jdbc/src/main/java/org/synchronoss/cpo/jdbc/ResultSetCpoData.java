/*
 * Copyright (C) 2003-2012 David E. Berry
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * A copy of the GNU Lesser General Public License may also be found at
 * http://www.gnu.org/licenses/lgpl.txt
 */
package org.synchronoss.cpo.jdbc;

import org.slf4j.*;
import org.synchronoss.cpo.CpoException;
import org.synchronoss.cpo.helper.ExceptionHelper;
import org.synchronoss.cpo.jdbc.meta.JdbcMethodMapEntry;
import org.synchronoss.cpo.jdbc.meta.JdbcMethodMapper;
import org.synchronoss.cpo.meta.domain.CpoAttribute;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

/**
 *
 * @author dberry
 */
public class ResultSetCpoData extends AbstractJdbcCpoData {

  private static final Logger logger = LoggerFactory.getLogger(CallableStatementCpoData.class);
  private ResultSet rs = null;

  public ResultSetCpoData(ResultSet rs, CpoAttribute cpoAttribute, int index) {
    super(cpoAttribute, index);
    this.rs = rs;
  }

  @Override
  public Object invokeGetter() throws CpoException {
    Object javaObject;
    JdbcMethodMapEntry<?> jdbcMethodMapEntry = JdbcMethodMapper.getJavaSqlMethod(getDataGetterReturnType());
    if (jdbcMethodMapEntry == null) {
      throw new CpoException("Error Retrieveing Jdbc Method for type: " + getDataGetterReturnType().getName());
    }

    try {
      // Get the getter for the Callable Statement
      javaObject = transformIn(jdbcMethodMapEntry.getRsGetter().invoke(rs, getIndex()));
    } catch (IllegalAccessException iae) {
      logger.debug("Error Invoking ResultSet Method: " + ExceptionHelper.getLocalizedMessage(iae));
      throw new CpoException(iae);
    } catch (InvocationTargetException ite) {
      logger.debug("Error Invoking ResultSet Method: " + ExceptionHelper.getLocalizedMessage(ite));
      throw new CpoException(ite.getCause());
    }

    return javaObject;
  }

}
