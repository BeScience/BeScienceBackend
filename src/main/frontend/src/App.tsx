import React from 'react';

import {Route, Routes} from "react-router-dom";

import {Layout} from "./Layout/Layout";
import LoginData from "./Component/LoginData";
import ExperienceData from "./Component/ExperienceData";
function App() {
  return (
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route path="logindata" element={<LoginData />} />
            <Route path="experiencedata" element={<ExperienceData />} />
          </Route>
        </Routes>
  );
}

export default App;
