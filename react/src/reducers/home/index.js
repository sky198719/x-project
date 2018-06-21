import {homeTest} from "../../action";

// Updates an entity cache in payload to any action with payload.entities.
export default function Home(state = initState, action) {
    switch (action.type) {
        case "HOME": {
            return homeTest.set(true);
        } default:
            return state;
    }
}

const initState = {
    homeState:false
};







