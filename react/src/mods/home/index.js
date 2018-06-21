import React from 'react';
import ReactDOM from 'react-dom';
import {O2Header , O2Footer} from '../../component/framework';
import {Content} from './view/index';
import {O2BackTop} from '../../component/backtop';
import registerServiceWorker from '../../registerServiceWorker';
import "../../css/base.scss";
import "./index.scss";
import {Provider} from 'react-redux';
import defaultStore from '../../store/defaultStore';
let store = defaultStore();
class App extends React.PureComponent {
	
    render() {
        return (
            <div>
                <O2Header pageIndex="home"/>
                <Content />
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