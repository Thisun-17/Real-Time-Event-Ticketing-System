// TestComponent.jsx
import React, { useState, useEffect } from 'react';

function TestComponent() {
    const [message, setMessage] = useState('');

    useEffect(() => {
        // Call the Spring Boot API
        fetch('http://localhost:8080/api/test')
            .then(response => response.text())
            .then(data => setMessage(data))
            .catch(error => console.error('Error:', error));
    }, []);

    return (
        <div>
            <h1>Message from backend: {message}</h1>
        </div>
    );
}

export default TestComponent;