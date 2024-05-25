import Login from "./Login";
import SignUp from "./SignUp";
import WriteImpression from "./WriteImpression";
import ModalImpression from "./impression/ModalImpression";
import Nothing from "./Nothing";
import View from "./View";
import Genre from "./Genre";
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
        <Route path="/member/save" element={<SignUp />} />
        <Route path="/board/bookSave" element={<WriteImpression />} />
        <Route path="board" element={<View />} />
        <Route path="nothing" element={<Nothing />} />
        <Route path="phrases" element={<Genre />} />
       </Routes>
       
    </BrowserRouter>
  );
}

export default App;