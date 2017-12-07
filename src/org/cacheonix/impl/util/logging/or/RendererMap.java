/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cacheonix.impl.util.logging.or;

import java.util.Hashtable;

import org.cacheonix.impl.util.logging.helpers.Loader;
import org.cacheonix.impl.util.logging.helpers.LogLog;
import org.cacheonix.impl.util.logging.helpers.OptionConverter;
import org.cacheonix.impl.util.logging.spi.RendererSupport;

/**
 * Map class objects to an {@link ObjectRenderer}.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since version 1.0
 */
public final class RendererMap {

   final Hashtable map;

   static final ObjectRenderer defaultRenderer = new DefaultRenderer();


   public RendererMap() {

      map = new Hashtable(11);
   }


   /**
    * Add a renderer to a hierarchy passed as parameter.
    *
    * @noinspection UnnecessaryReturnStatement
    */
   public static void addRenderer(final RendererSupport repository, final String renderedClassName,
                                  final String renderingClassName) {

      LogLog.debug("Rendering class: [" + renderingClassName + "], Rendered class: [" +
              renderedClassName + "].");
      final ObjectRenderer renderer = (ObjectRenderer)
              OptionConverter.instantiateByClassName(renderingClassName,
                      ObjectRenderer.class,
                      null);
      if (renderer == null) {
         LogLog.error("Could not instantiate renderer [" + renderingClassName + "].");
      } else {
         try {
            final Class renderedClass = Loader.loadClass(renderedClassName);
            repository.setRenderer(renderedClass, renderer);
         } catch (final ClassNotFoundException e) {
            LogLog.error("Could not find class [" + renderedClassName + "].", e);
         }
      }
   }


   /**
    * Find the appropriate renderer for the class type of the <code>o</code> parameter. This is accomplished by calling
    * the {@link #get(Class)} method. Once a renderer is found, it is applied on the object <code>o</code> and the
    * result is returned as a {@link String}.
    */
   public final String findAndRender(final Object o) {

      if (o == null) {
         return null;
      } else {
         return get(o.getClass()).doRender(o);
      }
   }


   /**
    * Syntactic sugar method that calls {@link #get(Class)} with the class of the object parameter.
    */
   public ObjectRenderer get(final Object o) {

      if (o == null) {
         return null;
      } else {
         return get(o.getClass());
      }
   }


   /**
    * Search the parents of <code>clazz</code> for a renderer. The renderer closest in the hierarchy will be returned.
    * If no renderers could be found, then the default renderer is returned.
    * <p/>
    * <p>The search first looks for a renderer configured for <code>clazz</code>. If a renderer could not be found, then
    * the search continues by looking at all the interfaces implemented by <code>clazz</code> including the
    * super-interfaces of each interface.  If a renderer cannot be found, then the search looks for a renderer defined
    * for the parent (superclass) of <code>clazz</code>. If that fails, then all the interfaces implemented by the
    * parent of <code>clazz</code> are searched and so on.
    * <p/>
    * <p>For example, if A0, A1, A2 are classes and X0, X1, X2, Y0, Y1 are interfaces where A2 extends A1 which in turn
    * extends A0 and similarly X2 extends X1 which extends X0 and Y1 extends Y0. Let us also assume that A1 implements
    * the Y0 interface and that A2 implements the X2 interface.
    * <p/>
    * <p>The table below shows the results returned by the <code>get(A2.class)</code> method depending on the renderers
    * added to the map.
    * <p/>
    * <p><table border="1"> <tr><th>Added renderers</th><th>Value returned by <code>get(A2.class)</code></th>
    * <p/>
    * <tr><td><code>A0Renderer</code> <td align="center"><code>A0Renderer</code>
    * <p/>
    * <tr><td><code>A0Renderer, A1Renderer</code> <td align="center"><code>A1Renderer</code>
    * <p/>
    * <tr><td><code>X0Renderer</code> <td align="center"><code>X0Renderer</code>
    * <p/>
    * <tr><td><code>A1Renderer, X0Renderer</code> <td align="center"><code>X0Renderer</code>
    * <p/>
    * </table>
    * <p/>
    * <p>This search algorithm is not the most natural, although it is particularly easy to implement. Future log4j
    * versions <em>may</em> implement a more intuitive search algorithm. However, the present algorithm should be
    * acceptable in the vast majority of circumstances.
    */
   public final ObjectRenderer get(final Class clazz) {
      ObjectRenderer r = null;
      for (Class c = clazz; c != null; c = c.getSuperclass()) {
         //System.out.println("Searching for class: "+c);
         r = (ObjectRenderer) map.get(c);
         if (r != null) {
            return r;
         }
         r = searchInterfaces(c);
         if (r != null) {
            return r;
         }
      }
      return defaultRenderer;
   }


   final ObjectRenderer searchInterfaces(final Class c) {
      //System.out.println("Searching interfaces of class: "+c);

      ObjectRenderer r = (ObjectRenderer) map.get(c);
      if (r != null) {
         return r;
      }

      final Class[] ia = c.getInterfaces();
      for (final Class anIa : ia) {
         r = searchInterfaces(anIa);
         if (r != null) {
            return r;
         }
      }
      return null;
   }


   public ObjectRenderer getDefaultRenderer() {

      return defaultRenderer;
   }


   public final void clear() {

      map.clear();
   }


   /**
    * Register an {@link ObjectRenderer} for <code>clazz</code>.
    */
   public final void put(final Class clazz, final ObjectRenderer or) {

      map.put(clazz, or);
   }
}
