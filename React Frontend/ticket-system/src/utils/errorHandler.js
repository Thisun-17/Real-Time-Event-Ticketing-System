// src/utils/errorHandler.js
export const handleApiError = (error) => {
  if (error.response) {
    // Server responded with error status
    return `Error: ${error.response.data.message || 'Something went wrong'}`;
  } else if (error.request) {
    // Request made but no response
    return 'Error: Could not connect to server';
  } else {
    // Other errors
    return 'Error: Something went wrong';
  }
};