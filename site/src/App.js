import React from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import Graph from "./components/Graph/Graph";

class App extends React.Component {
    apiBase = 'http://localhost:8080/api/v1/';

    constructor(props) {
        super(props);

        const node1 = {text: 'Default node 1', id: 1};

        this.state = {graph: {nodes: [], links: []}};
    }

    componentDidMount() {
        axios.get(this.apiBase + 'graph/sample')
            .then(res => {
                console.log('Got graph: ' + JSON.stringify(res));
                const graph = res.data;
                this.setState({ graph: graph });
            })
    }

    render() {
        return (
            <div className="diargam">
                <svg width="1000" height="800" version="1.1" xmlns="http://www.w3.org/2000/svg">


                    <Graph graph={this.state.graph}/>
                </svg>
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
