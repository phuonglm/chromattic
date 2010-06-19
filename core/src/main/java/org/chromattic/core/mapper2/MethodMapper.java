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

package org.chromattic.core.mapper2;

import org.chromattic.core.DomainSession;
import org.chromattic.core.EntityContext;
import org.chromattic.core.MethodInvoker;
import org.chromattic.core.ObjectContext;
import org.reflext.api.ClassTypeInfo;
import org.reflext.api.MethodInfo;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class MethodMapper<C extends ObjectContext> implements MethodInvoker<C> {

  /** . */
  private final MethodInfo method;

  public MethodMapper(MethodInfo method) {
    this.method = method;
  }

  public Object invoke(C context, Object[] args) throws Throwable {
    throw new UnsupportedOperationException();
  }

  public MethodInfo getMethod() {
    return method;
  }

  public Object invoke(C ctx, Method method, Object[] args) throws Throwable {
    return invoke(ctx, args);
  }

  @Override
  public String toString() {
    return "MethodMapper[" + ((Method)method.getMethod()).getDeclaringClass().getName() + "#" + method.getName() + "]";
  }

  public static class Create<C extends ObjectContext> extends MethodMapper<C> {

    /** . */
    ObjectMapper<C> mapper;

    public Create(MethodInfo method) {
      super(method);
    }

    @Override
    public Object invoke(C ctx, Object[] args) {

      //
      String name = null;
      if (args.length == 1 && args[0] != null && args[0] instanceof String) {
         name = (String)args[0];
      }

      //
      EntityContext entityCtx = ctx.getEntity();
      DomainSession session = entityCtx.getSession();
      Class<?> clazz = mapper.getObjectClass();
      return session.create(clazz, name);
    }
  }

  public static class FindById<C extends ObjectContext> extends MethodMapper<C> {

    /** . */
    private final Class<?> typeClass;

    public FindById(MethodInfo method, ClassTypeInfo type) throws ClassNotFoundException {
      super(method);

      //
      this.typeClass = Thread.currentThread().getContextClassLoader().loadClass(type.getName());
    }

    @Override
    public Object invoke(C context, Object[] args) throws Throwable {

      //
      String id = (String)args[0];

      //
      Object o = context.getEntity().getSession().findById(Object.class, id);

      //
      if (typeClass.isInstance(o)) {
        return o;
      }

      //
      return null;
    }
  }

  public static class Destroy extends MethodMapper<EntityContext> {

    public Destroy(MethodInfo method) {
      super(method);
    }

    @Override
    public Object invoke(EntityContext context, Object[] args) throws Throwable {
      context.getEntity().remove();
      return null;
    }
  }
}