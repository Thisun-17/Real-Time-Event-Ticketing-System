// api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// Add response interceptor for global error handling
api.interceptors.response.use(
    response => response,
    error => {
        const customError = {
            message: error.response?.data?.message || 'An unexpected error occurred',
            status: error.response?.status,
            timestamp: new Date().toISOString()
        };

        // Log error for debugging
        console.error('API Error:', customError);

        return Promise.reject(customError);
    }
);

// Expanded API service with additional endpoints
export const apiService = {
    // Ticket endpoints
    getTickets: () => api.get('/tickets'),
    createTicket: (ticketData) => api.post('/tickets', ticketData),
    updateTicket: (id, ticketData) => api.put(`/tickets/${id}`, ticketData),
    deleteTicket: (id) => api.delete(`/tickets/${id}`),
    getTicketById: (id) => api.get(`/tickets/${id}`),
    getTicketsByStatus: (status) => api.get(`/tickets/status/${status}`),

    // Vendor endpoints
    getVendors: () => api.get('/vendors'),
    getVendorById: (id) => api.get(`/vendors/${id}`),
    createVendor: (vendorData) => api.post('/vendors', vendorData),
    updateVendor: (id, vendorData) => api.put(`/vendors/${id}`, vendorData),

    // Customer endpoints
    getCustomers: () => api.get('/customers'),
    getCustomerById: (id) => api.get(`/customers/${id}`),
    createCustomer: (customerData) => api.post('/customers', customerData),
    updateCustomer: (id, customerData) => api.put(`/customers/${id}`, customerData),

    // Configuration endpoints
    getConfigurations: () => api.get('/configurations'),
    updateConfiguration: (id, configData) => api.put(`/configurations/${id}`, configData)
};

export default api;