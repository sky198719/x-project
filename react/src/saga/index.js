//import {loginDialog} from "../action";
//import { takeEvery } from 'redux-saga';
import { /*call, put,*/ all, takeLatest } from "redux-saga/effects";


//const delay = ms => new Promise(resolve => setTimeout(resolve, ms));

function* onTest1 (action){
    yield
    console.log ("test1 action");
    console.log (action);
    //loginDialog(false);
}

function* onTest2 () {
    yield
    console.log ("test2 action");
}


export default function* rootSaga() {

    console.log ("rootSaga")

    yield all([
       takeLatest("LOGIN", onTest2),
       takeLatest("LOGIN", onTest1),
    ]);
}


//export default [onTest1 , onTest2];