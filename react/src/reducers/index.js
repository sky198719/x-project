import {combineReducers} from 'redux';
import top from './backtop';
import home from './home';
const reducer = combineReducers({
    top , home
});
export default reducer;