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

package org.chromattic.core.mapper2.onetomany.hierarchical;

import org.chromattic.core.DomainSession;
import org.chromattic.core.EntityContext;
import org.chromattic.core.mapper2.JCRChildNodePropertyMapper;
import org.chromattic.metamodel.bean2.BeanValueInfo;
import org.chromattic.metamodel.bean2.SingleValuedPropertyInfo;
import org.chromattic.metamodel.mapping2.RelationshipMapping;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class JCRAnyChildCollectionPropertyMapper extends JCRChildNodePropertyMapper<SingleValuedPropertyInfo<BeanValueInfo>> {

  public JCRAnyChildCollectionPropertyMapper(RelationshipMapping.ManyToOne.Hierarchic info) throws ClassNotFoundException {
    super(info);
  }

  @Override
  public Object get(EntityContext context) throws Throwable {
    EntityContext parentCtx = context.getParent();
    if (parentCtx != null) {
      Class<?> relatedClass =  getRelatedClass();
      return parentCtx.adapt(relatedClass);
    }
    return null;
  }

  @Override
  public void set(EntityContext context, Object parent) throws Throwable {
    if (parent == null) {
      context.remove();
    } else {
      DomainSession session = context.getSession();
      EntityContext parentContext = session.unwrapEntity(parent);
      parentContext.addChild(context);
    }
  }
}