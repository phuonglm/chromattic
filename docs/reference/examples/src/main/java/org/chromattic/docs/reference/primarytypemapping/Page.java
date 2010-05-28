/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.chromattic.docs.reference.primarytypemapping;

import org.chromattic.api.annotations.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "page")
public abstract class Page {

  /**
   * Returns the page name.
   *
   * @return the page name
   */
  @Name
  public abstract String getName();

  /**
   * Returns the page title.
   *
   * @return the page title
   */
  @Property(name = "title")
  public abstract String getTitle(); // <1> The title property is mapped the string type

  /**
   * Updates the page title.
   *
   * @param title the new page title
   */
  public abstract void setTitle(String title);

  /**
   * Returns the date of the page last modification.
   *
   * @return the date of the last modification
   */
  @Property(name = "lastmodifieddate")
  public abstract Date getLastModifiedDate(); // <2> the last modified date property is mapped to a java.util.Calendar type

  /**
   * Updates the date of the page last modification.
   *
   * @param date the date of the last modification
   */
  public abstract void setLastModifiedDate(Date date);

  /**
   * Returns the list of tags of the page.
   *
   * @return the list of tags
   */
  @Property(name = "tags")
  public abstract List<String> getTags(); // <3> the tags property is mapped to a multi valued string type

  @OneToMany
  public abstract Collection<Page> getChildren();

  @ManyToOne
  public abstract Page getParent();

}
