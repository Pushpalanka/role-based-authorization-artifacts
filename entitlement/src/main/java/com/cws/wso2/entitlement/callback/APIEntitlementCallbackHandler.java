/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.cws.wso2.entitlement.callback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.rest.RESTConstants;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityUtils;
import org.wso2.carbon.apimgt.gateway.handlers.security.AuthenticationContext;
import org.wso2.carbon.identity.entitlement.mediator.callback.EntitlementCallbackHandler;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

public class APIEntitlementCallbackHandler extends EntitlementCallbackHandler {

    private static Log log = LogFactory.getLog(APIEntitlementCallbackHandler.class);

    public String getUserName(MessageContext synCtx) {
        AuthenticationContext authContext = APISecurityUtils.getAuthenticationContext(synCtx);
        String userName = null;
        if (authContext != null) {
            userName = MultitenantUtils.getTenantAwareUsername(authContext.getUsername());
        }
        log.info("Username :" + userName);
        return userName;
    }

    public String findServiceName(MessageContext synCtx) {

        return ((String) synCtx.getProperty(RESTConstants.REST_FULL_REQUEST_PATH)).split("/bar/1.0.0")[1];

    }

    public String findAction(MessageContext synCtx) {
        //        String action = (String) ((Axis2MessageContext) synCtx).getAxis2MessageContext().getProperty(
        //                org.apache.axis2.Constants.Configuration.HTTP_METHOD);

        //Customization to extract drink type from the URL as XACML action.
        String action;
        String serviceName = findServiceName(synCtx);
        int actionPlace = serviceName.split("drink").length;
        if (actionPlace > 1) {
            action = serviceName.split("drink")[actionPlace - 1].replace("/", "");
            log.info("Action : " + action);
        } else {
            action = "list"; // then this is a request coming to /drink without a drink type
            log.info("Action : " + action);
        }
        return action;
    }

    public String findOperationName(MessageContext synCtx) {
        return null;
    }

    public String[] findEnvironment(MessageContext synCtx) {
        return null;
    }
}
