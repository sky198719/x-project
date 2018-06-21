import React from 'react';
import { BackTop ,Modal } from 'antd';
import {loginDialog} from "../../action/index";
import "./index.scss";
import {connect} from 'react-redux';



function mapStateToProps(state) {
    return {
        dialogState:state.top.dialogState,
        homeState:true
    }
}

@connect(mapStateToProps)
export class O2BackTop extends React.PureComponent {
    setModalVisible = (modalVisible)=> {
        var me = this;
        if (modalVisible) {
            me.props.dispatch(loginDialog( true ));
        } else {
            me.props.dispatch(loginDialog( false ));
        }
    }

    render (){
        var me = this;
        let {dialogState} = me.props;
        return (
            <div className="backtop-wrap">
                <BackTop target={()=>{return document.getElementById("J_wrapBody")}} >
                    <div className="ant-back-top-inner"></div>
                </BackTop>
                <div className="top-qr-wrap">
                    <i className="top-qr" onClick={()=>me.setModalVisible(true)}></i>
                    <span className="qr-tip">扫码报名</span>
                </div>

                <Modal
                    wrapClassName="vertical-center-modal"
                    visible={dialogState}
                    //onOk={() => this.setModalVisible(false)}
                    onCancel={() => this.setModalVisible(false)}
                    footer={null}
                    width={350}
                >
                    <div className="dialog-title"><span>关注【】</span><br/>现在报名免费听</div>
                        <i className="dialog-qr">

                        </i>
                </Modal>

            </div>
        );
    }
}




