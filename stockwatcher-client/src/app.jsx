import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import Home from './Home.jsx';
import {Router, Route, IndexRoute, hashHistory} from 'react-router';
import $ from 'jquery';


class App extends Component {

	constructor(props) {
		super(props);
		
	}

    render() {
        return (
            <div>
                <div className="container">
                    {this.props.children}
                </div>
            </div>
        );
    }
}



var destination = document.querySelector("#container");

ReactDOM.render(
    <Router history={hashHistory}>
    <Route path="/" component={App}>
        <IndexRoute component={Home}/>
    </Route>
</Router>, destination);