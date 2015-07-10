/*
 * Cacheonix systems licenses this file to You under the LGPL 2.1
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.cacheonix.com/products/cacheonix/license-lgpl-2.1.htm
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cacheonix.impl.util.hashcode;

import junit.framework.TestCase;

/**
 * HashCodeCalculatorType Tester.
 *
 * @author simeshev@cacheonix.org
 * @version 1.0
 * @since <pre>04/13/2008</pre>
 */
public final class HashCodeCalculatorTypeTest extends TestCase {

   public HashCodeCalculatorTypeTest(final String name) {

      super(name);
   }


   public void testToString() {

      assertNotNull(HashCodeCalculatorType.FNV1A32.toString());
      assertNotNull(HashCodeCalculatorType.MD5.toString());
   }


   public void testHashCode() {

      assertTrue(HashCodeCalculatorType.FNV1A32.hashCode() != 0);
      assertTrue(HashCodeCalculatorType.MD5.hashCode() != 0);
   }


   public void testEquals() {

      assertTrue(!HashCodeCalculatorType.FNV1A32.equals(HashCodeCalculatorType.MD5));
   }
}
