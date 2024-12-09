// src/components/TicketDisplay.js
import React, { useState, useEffect } from 'react';
import { apiService } from '../services/api';

const TicketDisplay = () => {
    const [tickets, setTickets] = useState({
        available: 0,
        sold: 0,
        total: 0
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchTickets();
    }, []);

    const fetchTickets = async () => {
        try {
            setLoading(true);
            const response = await apiService.getTickets();

            // Process tickets data
            const ticketCounts = response.data.reduce((acc, ticket) => {
                if (ticket.status === 'SOLD') {
                    acc.sold += 1;
                } else {
                    acc.available += 1;
                }
                return acc;
            }, { available: 0, sold: 0 });

            ticketCounts.total = ticketCounts.available + ticketCounts.sold;
            setTickets(ticketCounts);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    if (loading) return (
        <div className="flex justify-center items-center h-48">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
        </div>
    );

    if (error) return (
        <div className="bg-red-50 text-red-700 p-4 rounded-md">
            Error: {error}
        </div>
    );

    return (
        <div className="status-section p-4">
            <div className="grid grid-cols-2 gap-4 mb-6">
                <div className="bg-white p-6 rounded-lg shadow">
                    <h3 className="text-lg font-semibold mb-2">Available Tickets</h3>
                    <p className="text-3xl font-bold text-blue-600">{tickets.available}</p>
                </div>
                <div className="bg-white p-6 rounded-lg shadow">
                    <h3 className="text-lg font-semibold mb-2">Sold Tickets</h3>
                    <p className="text-3xl font-bold text-green-600">{tickets.sold}</p>
                </div>
            </div>
            <div className="bg-white p-4 rounded-lg shadow">
                <p className="text-lg">Total Tickets: <span className="font-bold">{tickets.total}</span></p>
            </div>
        </div>
    );
};

export default TicketDisplay;