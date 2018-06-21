import React from 'react';
import ReactDOM from 'react-dom';
import {O2Header , O2Footer} from '../../component/framework';
import {Content} from './view/index';
import { O2BackTop } from '../../component/backtop';
import registerServiceWorker from '../../registerServiceWorker';
import "../../css/base.scss";
import "./index.scss";
import {loginDialog} from "../../action/index";
import {Provider} from 'react-redux';
import defaultStore from '../../store/defaultStore';
import {connect} from 'react-redux';
let store = defaultStore();
function mapStateToProps(state) {
    return {
        dialogState:state.top.dialogState
    }
}
@connect(mapStateToProps)
class App extends React.PureComponent {
    fnSign = (e)=>{
        var me = this;
        me.props.dispatch(loginDialog( true ));
        e && e.preventDefault();
    }
    
    render() {
        var me = this;
        return (
            <div>
                <O2Header pageIndex="list"/>
                <Content fnSign={me.fnSign} />
                <O2Footer />
                <O2BackTop/>
            </div>
        );
    }
}
ReactDOM.render(<Provider store={store} >
    <App />
</Provider>, document.getElementById('J_wrapBody'));
registerServiceWorker();