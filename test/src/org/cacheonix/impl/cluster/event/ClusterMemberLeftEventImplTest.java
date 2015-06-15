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
package org.cacheonix.impl.cluster.event;

import java.util.ArrayList;
import java.util.Collection;

import org.cacheonix.cluster.ClusterMember;
import junit.framework.TestCase;

/**
 * Tester for ClusterMemberLeftEventImpl.
 */
public class ClusterMemberLeftEventImplTest extends TestCase {


   private ClusterMemberImpl clusterMember;

   private ClusterMemberLeftEventImpl clusterMemberLeftEvent;


   public void testGetLeftMembers() throws Exception {

      final Collection<ClusterMember> leftMembers = clusterMemberLeftEvent.getLeftMembers();
      assertEquals(clusterMember, leftMembers.iterator().next());
   }


   public void testToString() throws Exception {

      assertNotNull(clusterMemberLeftEvent.toString());
   }


   public void setUp() throws Exception {

      super.setUp();

      clusterMember = EventTestUtil.clusterMember("TestClusterName", "1.1.1.1", 7777);
      final ArrayList<ClusterMember> leftMembers = new ArrayList<ClusterMember>(1);
      leftMembers.add(clusterMember);

      clusterMemberLeftEvent = new ClusterMemberLeftEventImpl(leftMembers);
   }


   public void tearDown() throws Exception {


      clusterMemberLeftEvent = null;
      clusterMember = null;

      super.tearDown();
   }


   public String toString() {

      return "ClusterMemberLeftEventImplTest{" +
              "clusterMember=" + clusterMember +
              ", clusterMemberLeftEvent=" + clusterMemberLeftEvent +
              "} " + super.toString();
   }
}