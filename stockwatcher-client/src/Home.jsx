import React, {Component} from 'react';
import {Glyphicon, Label, Button} from 'react-bootstrap';
import RNEventSource from 'react-native-event-source';
import $ from 'jquery';

export default class Home extends Component {
    constructor(props) {
        super(props);
		
		this.state = {
			stocks: [],
			running: false
		};
		
		this.processData = this.processData.bind(this)
		this.onClickStart = this.onClickStart.bind(this)
		this.onClickStop = this.onClickStop.bind(this)
    }
	
	componentDidMount(){
		 $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "http://localhost:8080/classic/all",
            dataType: 'json',
            success: function(result) {
                this.setState({stocks: result});
            }.bind(this),
        });
	}
	
	componentWillUnmount(){
		this.eventSource.removeAllListeners();
		this.eventSource.close();
	}
	
	processData(stock){
		var symbol = stock.symbol;
		var tmpStocks = this.state.stocks;
		var index = -1;
		
		for(var i = 0; i < tmpStocks.length; i++){
			if(tmpStocks[i].symbol === symbol){
				index = i;
			}
		}
		
		if(index >= 0){
			tmpStocks[index] = stock;
		}
		else{
			tmpStocks.push(stock);
		}
		
		this.setState({
			stocks: tmpStocks
		});
	}
	
	onClickStart(){
		console.log("start");
		this.eventSource = new RNEventSource('http://localhost:8080/classic/test');
		this.eventSource.addEventListener('message', (event) => {
			var stock = JSON.parse(event.data);
			this.processData(stock);			
		});
		
		this.setState({running: true});
	}

	onClickStop(){
		console.log("stop");
		this.eventSource.removeAllListeners();
		this.eventSource.close();
		this.setState({running: false});
	}
	
    render() {
        return (
            <div>
                <h2>Reactive Stockwatcher</h2>
                <div>
				<Button bsStyle="danger" onClick={this.onClickStop} disabled={!this.state.running}>Stop</Button>
				<Button bsStyle="success" onClick={this.onClickStart} disabled={this.state.running}>Start</Button>
                </div>
					<table className="table table-striped">
						<thead>
							<tr>
								<th>Symbol</th>
								<th>Price</th>
								<th>Price Change</th>
								<th>LastUpdate</th>
							</tr>
						</thead>
						<tbody>
							{this.state.stocks.map(function(stock, i) {
								return <tr key={i}>
									<td>{stock.symbol}</td>
									<td>{stock.price}</td>
									<td>
										<div style={{textAlign: "right", width: 60}}>
										{stock.priceChange}
										{stock.priceChange > 0 ? 
										<Glyphicon style={{color: 'green'}} glyph="arrow-up" /> : 
										<Glyphicon style={{color: 'red'}} glyph="arrow-down" />}
										</div>
									</td>
									<td>{stock.lastUpdate}</td>
								</tr>
							}.bind(this))}
						</tbody>
					</table>	
				</div>
        );
    }
}
