import {createStore, applyMiddleware} from 'redux';
import reducers from '../reducers/index';
import createSagaMiddleware from 'redux-saga';
import sagas from '../saga/index';
let sagaMiddleware = createSagaMiddleware();
export default function defaultStore(initialState) {
    let store = createStore(reducers, initialState, applyMiddleware( sagaMiddleware ));
    sagaMiddleware.run(sagas);
    return store;
}