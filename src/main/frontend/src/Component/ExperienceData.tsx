import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import { Bar, Line, Pie } from 'react-chartjs-2';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

interface StampDTO {
    nickname: string;
    enter: string;
    activity: string;
    startAt: string;
    endAt: string;
}

const ExperienceData = () => {
    const [experienceData, setExperienceData] = useState<StampDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [chartType, setChartType] = useState<string>('bar');

    useEffect(() => {
        const fetchExperienceData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/stamp/all');
                setExperienceData(response.data);
            } catch (error) {
                setError('Error fetching experience data');
                console.error('Error fetching experience data:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchExperienceData();
    }, []);

    useEffect(() => {
        // 새로운 데이터가 추가될 때마다 차트 데이터를 업데이트
    }, [experienceData]);

    if (loading) {
        return <LoadingMessage>Loading...</LoadingMessage>;
    }

    if (error) {
        return <ErrorMessage>{error}</ErrorMessage>;
    }

    const activityCounts = experienceData.reduce((acc, data) => {
        acc[data.enter] = (acc[data.enter] || 0) + 1;
        return acc;
    }, {} as Record<string, number>);

    const chartData = {
        labels: Object.keys(activityCounts),
        datasets: [
            {
                label: 'Enter',
                data: Object.values(activityCounts),
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
            },
        ],
    };

    return (
        <Container>
            <h1>Stamp Records</h1>
            <Table>
                <thead>
                <tr>
                    <th>Nickname</th>
                    <th>Enter</th>
                    <th>Activity</th>
                    <th>StartAt</th>
                    <th>EndAt</th>
                </tr>
                </thead>
                <tbody>
                {experienceData.map((data, index) => (
                    <tr key={index}>
                        <td>{data.nickname}</td>
                        <td>{data.enter}</td>
                        <td>{data.activity}</td>
                        <td>{data.startAt}</td>
                        <td>{data.endAt}</td>
                    </tr>
                ))}
                </tbody>
            </Table>

            <ChartContainer>
                <h2>Activity Distribution</h2>
                <ChartSelector>
                    <label>차트 종류 선택: </label>
                    <select onChange={(e) => setChartType(e.target.value)} value={chartType}>
                        <option value="bar">바 차트</option>
                        <option value="line">라인 차트</option>
                        <option value="pie">파이 차트</option>
                    </select>
                </ChartSelector>
                {chartType === 'bar' && <Bar data={chartData} />}
                {chartType === 'line' && <Line data={chartData} />}
                {chartType === 'pie' && <Pie data={chartData} />}
            </ChartContainer>
        </Container>
    );
};

const Container = styled.div`
  padding: 20px;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;

  th, td {
    border: 1px solid black;
    padding: 8px;
    text-align: left;
  }

  th {
    background-color: #f2f2f2;
  }
`;

const LoadingMessage = styled.div`
  font-size: 18px;
  text-align: center;
  margin-top: 20px;
`;

const ErrorMessage = styled.div`
  color: red;
  font-size: 18px;
  text-align: center;
  margin-top: 20px;
`;

const ChartContainer = styled.div`
  margin-top: 30px;
  width: 100%;
  max-width: 800px; /* 차트의 최대 가로 길이 */
  height: 400px; /* 차트의 높이 */
`;


const ChartSelector = styled.div`
  margin-bottom: 20px;
`;

export default ExperienceData;





