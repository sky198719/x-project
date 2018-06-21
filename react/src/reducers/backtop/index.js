const initState = {
    dialogState:false
};
export default function Top(state = initState, action) {
    switch (action.type) {
        case "LOGIN": {
            console.log ("LOGIN" + action.dialogState);
            return {
                dialogState:action.dialogState
            }
        }
        default : 
            return state;
    }
}



