import React from 'react';
import {ExperienceData} from "./Component/ExperienceData";
import {Route, Routes} from "react-router-dom";
import {LoginData} from "./Component/LoginData";
import {Layout} from "./Layout/Layout";
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
