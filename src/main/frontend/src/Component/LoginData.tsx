import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginData = () => {
    const [loginData, setLoginData] = useState(null);

    useEffect(() => {
        const fetchLoginData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/login'); // 실제 API URL로 변경
                setLoginData(response.data);
            } catch (error) {
                console.error('Error fetching login data:', error);
            }
        };

        fetchLoginData();
    }, []);

    return (
        <div>
            <h1>Login Data</h1>
            {loginData && <pre>{JSON.stringify(loginData, null, 2)}</pre>}
        </div>
    );
};

export default LoginData;
