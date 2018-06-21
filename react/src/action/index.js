export const loginDialog = (dialogState) => {
    console.log (dialogState);
    return {
        type: 'LOGIN', //reducer action type
        dialogState
    };
};



export const homeTest = (homeState) => {
    return {
        type: 'HOME',
        homeState
    };
};