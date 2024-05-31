import Login from "./Login";
import PopUp from "./popUp";
import SignUp from "./SignUp";
import WriteImpression from "./WriteImpression";
import View from "./View";
import axios from 'axios';
import React, {useState, useEffect} from 'react';
import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

function App() {

  /*const [posts, setPosts] = useState([]);
  useEffect(() => {
    axios({
      method:'GET',
      url:'https://jsonplaceholder.typicode.com/posts'
    }).then(response => setPosts(response.data))
  })

  <div>
    <ul>
    {posts.map(post => (
      <li key={post.id}>{post.title}</li>
    ))}
    </ul>
  </div>*/
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Login />} />
        <Route path="popup" element={<PopUp />} />
        <Route path="sign-up" element={<SignUp />} />
        <Route path="write-impression" element={<WriteImpression />} />
        <Route path="view" element={<View />} />
       </Routes>
    </BrowserRouter>
  );
}

export default App;