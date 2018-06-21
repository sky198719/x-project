import React from 'react';
import "./index.scss";
import {loginDialog} from "../../action/index";
import {connect} from 'react-redux';
function mapStateToProps(state) {
    return {
        dialogState:state.top.dialogState
    }
}
@connect(mapStateToProps)
export class O2Header extends React.PureComponent {
     fnSign = (e)=>{
         var me = this;
         me.props.dispatch(loginDialog( true ));
         e && e.preventDefault();
     }
    
    render (){
        return (
            <div className="header-wrap">
                header
            </div>
        );
    }
}


export class O2Footer extends React.PureComponent {
    render (){
        return (
            <div className="footer-wrap">
				footer
            </div>
        );
    }
}


