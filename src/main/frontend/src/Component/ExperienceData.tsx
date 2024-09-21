import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ExperienceData = () => {
    const [experienceData, setExperienceData] = useState(null);

    useEffect(() => {
        const fetchExperienceData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/experience'); // 실제 API URL로 변경
                setExperienceData(response.data);
            } catch (error) {
                console.error('Error fetching experience data:', error);
            }
        };

        fetchExperienceData();
    }, []);

    return (
        <div>
            <h1>Experience Data</h1>
            {experienceData && <pre>{JSON.stringify(experienceData, null, 2)}</pre>}
        </div>
    );
};

export default ExperienceData;
