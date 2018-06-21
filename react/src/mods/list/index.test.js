import React from 'react';
import ReactDOM from 'react-dom';
import {Content} from './view/index';
import "../../css/base.scss";
import "./index.scss";

class App extends React.PureComponent {
    render() {
        return (
            <div>
                <Content  />
            </div>
        );
    }
}
it('renders without crashing list', () => {
  const div = document.createElement('div');
  ReactDOM.render(<App/>, div);
  ReactDOM.unmountComponentAtNode(div);
});
