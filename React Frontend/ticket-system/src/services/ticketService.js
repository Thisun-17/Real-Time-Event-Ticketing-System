// src/services/ticketService.js
const API_BASE_URL = 'http://localhost:8080/api';

export const ticketService = {
  // Configuration methods
  async getConfig() {
    const response = await fetch(`${API_BASE_URL}/config`);
    return response.json();
  },

  async updateConfig(config) {
    const response = await fetch(`${API_BASE_URL}/config`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(config),
    });
    return response.json();
  },

  // System control methods
  async startSystem() {
    const response = await fetch(`${API_BASE_URL}/system/start`, {
      method: 'POST',
    });
    return response.json();
  },

  async stopSystem() {
    const response = await fetch(`${API_BASE_URL}/system/stop`, {
      method: 'POST',
    });
    return response.json();
  },

  // Ticket methods
  async getTicketStatus() {
    const response = await fetch(`${API_BASE_URL}/tickets/status`);
    return response.json();
  },
};