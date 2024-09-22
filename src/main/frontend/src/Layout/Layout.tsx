import {Link, Outlet} from "react-router-dom";
import React from "react";

export const Layout : React.FC = () => {
    return (
        <div style={{ display: 'flex', height: '100vh' }}>
            {/* 왼쪽 네비게이션 바 */}
            <nav style={{
                width: '200px',
                backgroundColor: '#007bff',
                padding: '20px',
                color: 'white',
                minHeight: '120vh'  /* 화면 전체 세로 길이 */
            }}>
                <h2 style={{ color: 'black' }}>BeScience</h2>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    <li>
                        <Link to="/experiencedata" style={{ color: 'white', textDecoration: 'none' }}>ExperienceData</Link>
                    </li>
                </ul>
            </nav>


            {/* 메인 컨텐츠 (페이지 이동시 Outlet에 따라 변경) */}
            <div style={{ flex: 1, padding: '20px', backgroundColor: 'white' }}>
                <Outlet />
            </div>
        </div>
    );
}