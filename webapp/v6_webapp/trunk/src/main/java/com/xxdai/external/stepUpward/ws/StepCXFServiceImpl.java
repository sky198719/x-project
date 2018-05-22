
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.xxdai.external.stepUpward.ws;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-12-01T15:09:23.634+08:00
 * Generated source version: 2.7.12
 * 
 */

@javax.jws.WebService(
                      serviceName = "StepCXFServiceImplService",
                      portName = "StepCXFServiceImplPort",
                      targetNamespace = "http://impl.webservice.step.xxdai.com/",
                      wsdlLocation = "http://test.xxd.com/yx_service/service/stepWebService?wsdl",
                      endpointInterface = "com.xxdai.external.stepUpward.ws.StepCXFService")
                      
public class StepCXFServiceImpl implements StepCXFService {

    private static final Logger LOG = Logger.getLogger(StepCXFServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getStepList(java.lang.String  params )*
     */
    public java.lang.String getStepList(java.lang.String params) { 
        LOG.info("Executing operation getStepList");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#preQuitCheck(java.lang.String  params )*
     */
    public java.lang.String preQuitCheck(java.lang.String params) { 
        LOG.info("Executing operation preQuitCheck");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getStepGAData(java.lang.String  params )*
     */
    public java.lang.String getStepGAData(java.lang.String params) { 
        LOG.info("Executing operation getStepGAData");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getQuitDesc(java.lang.String  params )*
     */
    public java.lang.String getQuitDesc(java.lang.String params) { 
        LOG.info("Executing operation getQuitDesc");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getStepForIndex(*
     */
    public java.lang.String getStepForIndex() { 
        LOG.info("Executing operation getStepForIndex");
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#queryEndStepJoinList(java.lang.String  params )*
     */
    public java.lang.String queryEndStepJoinList(java.lang.String params) { 
        LOG.info("Executing operation queryEndStepJoinList");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getJoinRecord(java.lang.String  jsonString )*
     */
    public java.lang.String getJoinRecord(java.lang.String jsonString) { 
        LOG.info("Executing operation getJoinRecord");
        System.out.println(jsonString);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#quit(java.lang.String  jsonString )*
     */
    public java.lang.String quit(java.lang.String jsonString) { 
        LOG.info("Executing operation quit");
        System.out.println(jsonString);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#updateStepStatus(java.lang.String  stepId )*
     */
    public java.lang.String updateStepStatus(java.lang.String stepId) { 
        LOG.info("Executing operation updateStepStatus");
        System.out.println(stepId);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#queryTendingStepJoinList(java.lang.String  params )*
     */
    public java.lang.String queryTendingStepJoinList(java.lang.String params) { 
        LOG.info("Executing operation queryTendingStepJoinList");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#statisticStepJoin(java.lang.String  userId )*
     */
    public java.lang.String statisticStepJoin(java.lang.String userId) { 
        LOG.info("Executing operation statisticStepJoin");
        System.out.println(userId);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#queryStepQuitList(java.lang.String  params )*
     */
    public java.lang.String queryStepQuitList(java.lang.String params) { 
        LOG.info("Executing operation queryStepQuitList");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#buyStep(java.lang.String  jsonString )*
     */
    public java.lang.String buyStep(java.lang.String jsonString) { 
        LOG.info("Executing operation buyStep");
        System.out.println(jsonString);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#findStepJoinById(java.lang.String  stepJoinId )*
     */
    public java.lang.String findStepJoinById(java.lang.String stepJoinId) { 
        LOG.info("Executing operation findStepJoinById");
        System.out.println(stepJoinId);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getStepById(java.lang.String  jsonString )*
     */
    public java.lang.String getStepById(java.lang.String jsonString) { 
        LOG.info("Executing operation getStepById");
        System.out.println(jsonString);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#updateStep(java.lang.String  params )*
     */
    public java.lang.String updateStep(java.lang.String params) { 
        LOG.info("Executing operation updateStep");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#addStep(java.lang.String  params )*
     */
    public java.lang.String addStep(java.lang.String params) { 
        LOG.info("Executing operation addStep");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.stepUpward.ws.StepCXFService#getStepAccount(*
     */
    public java.lang.String getStepAccount() { 
        LOG.info("Executing operation getStepAccount");
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}