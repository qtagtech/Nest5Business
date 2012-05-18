/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nest5.androidClient;

/**
 * Class to be customized with app-specific data. The Eclipse plugin will set
 * these values when the project is created.
 */
public class Setup {

    /**
     * The AppEngine app name, used to construct the production service URL
     * below.
     */
    private static final String APP_NAME = "Nest5";
    

    /**
     * The URL of the production service.
     */
    public static final String PROD_URL = "http://www.nest5.com";
    
    public static final String DEV_URL = "http://nest5testserver.herokuapp.com";

    /**
     * The C2DM sender ID for the server. A C2DM registration with this name
     * must exist for the app to function correctly.
     */
    public static final String SENDER_ID = "juanda6@gmail.com";
    
    public static final String apiKey = "zAxEE9U1691Nq0h5JiJ0X20tcayF5RTpmzIOctVGNQNBByHslznDR0VP7rWOuyW";
}
