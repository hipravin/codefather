import React from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import Graph from "./components/Graph/Graph";

class App extends React.Component {
    apiBase = 'http://localhost:8080/api/v1/';

    tick = 0;
    callInProgress = false;

    constructor(props) {
        super(props);

        const node1 = {text: 'Default node 1', id: 1};

        this.state = {graph: {nodes: [], links: []}};
    }

    componentDidMount() {

        setInterval(() => {
            // var graphId = 'sample-1';
            var graphId = 'sample-r120';
            if(!this.callInProgress) {
                this.callInProgress = true;//well, that's definitely not atomic
                axios.get(this.apiBase + `graph/${graphId}/simulation/` + this.tick)
                    .then(res => {
                        console.log('Got graph: ' + JSON.stringify(res));
                        const graph = res.data.graph;
                        this.setState({graph: graph});
                        this.tick = res.data.tick + 1;
                        this.callInProgress = false;
                    });
            }
        }, 50);

        // axios.get(this.apiBase + 'graph/sample')
        //     .then(res => {
        //         console.log('Got graph: ' + JSON.stringify(res));
        //         const graph = res.data;
        //         this.setState({ graph: graph });
        //     });
    }

    render() {
        return (
            <div>
                <svg className = "diagram" width="1600" height="900" version="1.1" xmlns="http://www.w3.org/2000/svg">
                    <Graph graph={this.state.graph}/>
                </svg>
                <p>Tick: {this.tick}</p>
            </div>
            // <div className="App">
            //     <header className="App-header">
            //         <Graph graph={this.state.graph}/>
            //
            //         <img src={logo} className="App-logo" alt="logo"/>
            //     </header>
            // </div>
        )
    }
}

export default App;
