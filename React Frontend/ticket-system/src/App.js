import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import TicketList from './components/TicketList';
import TicketForm from './components/TicketForm';

function App() {
    return (
        <Router>
            <div>
                <div className="container mx-auto p-4">
                    <Routes>
                        <Route path="/" element={<TicketList />} />
                        <Route path="/tickets" element={<TicketList />} />
                        <Route path="/tickets/new" element={<TicketForm />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;