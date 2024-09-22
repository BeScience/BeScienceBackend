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

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [itemsPerPage] = useState<number>(10);
    const [startDate, setStartDate] = useState<string>('');
    const [endDate, setEndDate] = useState<string>('');

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

    if (loading) {
        return <LoadingMessage>Loading...</LoadingMessage>;
    }

    if (error) {
        return <ErrorMessage>{error}</ErrorMessage>;
    }

    // 날짜 필터링
    const filteredData = experienceData.filter(data => {
        const dataDate = new Date(data.startAt); // 날짜 형식에 따라 조정 필요
        const start = startDate ? new Date(startDate) : null;
        const end = endDate ? new Date(endDate) : null;

        return (!start || dataDate >= start) && (!end || dataDate <= end);
    });

    // 페이지네이션 처리
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredData.slice(indexOfFirstItem, indexOfLastItem);

    const totalPages = Math.ceil(filteredData.length / itemsPerPage);

    // 필터링된 데이터를 기반으로 차트 데이터 생성
    const activityCounts = filteredData.reduce((acc, data) => {
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

    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    return (
        <Container>
            <h1>Stamp Records</h1>

            <DateFilter>
                <label>시작일:</label>
                <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
                <label>종료일:</label>
                <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
            </DateFilter>

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
                {currentItems.map((data, index) => (
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

            <Pagination>
                <button onClick={handlePreviousPage} disabled={currentPage === 1}>이전</button>
                <span>{currentPage} / {totalPages}</span>
                <button onClick={handleNextPage} disabled={currentPage === totalPages}>다음</button>
            </Pagination>

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

const DateFilter = styled.div`
  margin-bottom: 20px;
  display: flex;
  align-items: center;

  label {
    margin: 0 10px;
  }

  input {
    margin: 0 10px;
  }
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

const Pagination = styled.div`
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;

  button {
    margin: 0 10px;
    padding: 5px 10px;
  }

  span {
    font-size: 16px;
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
  max-width: 800px;
  height: 400px;
`;

const ChartSelector = styled.div`
  margin-bottom: 20px;
`;

export default ExperienceData;









