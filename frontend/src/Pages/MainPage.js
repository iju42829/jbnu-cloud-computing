import React, { useState, useEffect } from "react";
import { data, useLocation, useNavigate } from 'react-router-dom';
import './MainPage.css'
 
  //로그인 창에서 넘어옴
  //사용자 정보(이메일, 이름, 프로필 사진(?)), 파일 저장소 정보, 옵션 정보를 받아옴
  //헤더의 사용자 부분을 사용자 정보로 초기화
  //메인 컨테이너의 내용을 파일 저장소 정보로 초기화
  //최근 파일의 최대 개수 제한
  //초기화 시'내 저장소'로 이동하도록 함
  //페이지 새로고침 시 다시 초기화하도록 함
  //사이드 컨테이너의 내 저장소 부분을 초기화
  //storage의 구성을 확인할 수 있도록 함.
  //파일 제외, 폴더만 표시.
  //옵션 파일의 내용에 맞춰 사용자 설정 초기화(전체 뷰에 관여)
  //옵션이 변경될 경우 확인 버튼을 눌렀을 때 옵션 파일의 내용을 수정하고, 업데이트하는 형식


function MainPage() {
  //페이지 이동, 이전 페이지의 데이터 가져오기
  const navigate = useNavigate();  
  const location = useLocation();
<<<<<<< Updated upstream
  const {userInfo, storageInfo, options} = location.state || {};

  const [files, setFiles] = useState(storageInfo || []);

=======
  const {userInfo, options} = location.state || {};
  const [currentPath, setCurrentPath] = useState("/storage");

  useEffect(() => {
    updatePath("/storage");
  },[]);
  const [files, setFiles] = useState([]);

  const updatePath = async (path) => {
    console.log(`${path}`);
    const response = await fetch(`${path}.json`);
    if(!response.ok) {
       throw new Error('http error');
      }
      const data = await response.json();
      setFiles(data);
      setCurrentPath(path);
    }
>>>>>>> Stashed changes

  //현재 위치 관리
  const Path = "/내 저장소"

  //드롭다운 관리
  const UserDropdown = () => { //유저 창 드롭다운
    const [isDropdownView, setDropdownView] = useState(false)
    const viewDropdown = () => {
      setDropdownView(!isDropdownView)
    }
   
    return(
      //  모션 추가할 예정
      <>
        {isDropdownView && <button className="userbtn">계정관리</button>}
        {isDropdownView && <button className="userbtn" onClick={() => navigate('/')}>로그아웃</button>}
        <button className="userbtn" onClick={viewDropdown}>
          <img src={userInfo?.profileImage} onError={(e) => {e.target.onerror = null; 
            e.target.src = "image/user.png"}} className="headerimg"/> 
          <h>{userInfo?.name}</h>
        </button>
      </>
    )
  }

<<<<<<< Updated upstream
  //팝업창 관리
=======
  //폴더 현황 드롭다운
  const [openStorage, setOpenStorage] = useState({});
  const storageDropdown = (folderList, path = "/storage") => {
    return folderList.map( (item) => {
      
    });
  }
  //팝업창 관리 --------------------------------------------------------------------
  //새 폴더 팝업
>>>>>>> Stashed changes
  const [isFolderPopupOpen, setFolderPopupOpen] = useState(false);
  const [folderName, setFolderName] = useState("");
  const openFolderPopup = () => {
    setFolderPopupOpen(true);
  };

  const closeFolderPopup = () => {
    setFolderPopupOpen(false);
    setFolderName(""); // 입력 필드 초기화
  };

  const handleCreateFolder = () => {
    console.log(`새 폴더 이름: ${folderName}`);
    if (folderName.trim() === '') {
      alert("폴더 이름을 입력하세요.")
      return;
    }
    const newFolder = {
      name: folderName,
      type: "folder",
      children: []
    };

    setFiles((prevFiles) => [...prevFiles, newFolder]);

    // 데이터베이스 적용 로직 추가
    closeFolderPopup(); // 팝업 닫기
  };

  //정렬 관리
  const [sortKey, setSortKey] = useState("name"); // 기본 정렬 기준
  const [sortOrder, setSortOrder] = useState("asc"); // 기본 정렬 순서
  const handleSort = (key) => {
    if (sortKey === key) {
      // 동일 키 클릭 시 정렬 방향 토글
      setSortOrder((prevOrder) => (prevOrder === "asc" ? "desc" : "asc"));
    } else {
      // 다른 키 클릭 시 정렬 기준 변경 및 오름차순 초기화
      setSortKey(key);
      setSortOrder("asc");
    }
  };
  const sortedFiles = [...files].sort((a, b) => {
    // 폴더 우선 정렬
    if (a.type === "folder" && b.type !== "folder") return -1;
    if (a.type !== "folder" && b.type === "folder") return 1;

    let valueA = a[sortKey];
    let valueB = b[sortKey];

    // 특정 키에 대한 비교
    if (sortKey === "lastModified") {
      valueA = new Date(valueA || 0);
      valueB = new Date(valueB || 0);
    } else if (sortKey === "size") {
      valueA = valueA || 0;
      valueB = valueB || 0;
    }

    if (valueA < valueB) return sortOrder === "asc" ? -1 : 1;
    if (valueA > valueB) return sortOrder === "asc" ? 1 : -1;
    return 0;
  });

<<<<<<< Updated upstream
=======
  //파일 선택 관리 -----------------------------------------------------------------
  const [selectedFiles, setSelectedFiles] = useState([]); // 선택된 파일/폴더
  const [previewFile, setPreviewFile] = useState(null); // 미리보기 파일
  const handleContainerClick = (event) => {// 클릭 시 강조 표시
    if (!event.target.closest("tr")) { // 클릭한 곳이 파일/폴더가 아닌 경우에만 선택 해제
      setSelectedFiles([]);
    }
  };
  const handleClick = (file, event) => {
    if (event.ctrlKey) {// Ctrl 누르고 클릭하면 다중 선택
      setSelectedFiles((prevSelected) =>
        prevSelected.includes(file)
          ? prevSelected.filter((f) => f !== file) // 이미 선택된 경우 해제
          : [...prevSelected, file]
      );
    } else {
      setSelectedFiles([file]);// 단일 선택
    }
  };
  const handleDoubleClick = (file) => { // 더블 클릭 이벤트
    if (file.type === "folder") {
      updatePath(`${currentPath}/${file.name}`);
    } else {
      // 파일인 경우 미리보기 표시
      setPreviewFile(file);
    }
  };
  const handleGoBack = () => {
    if(currentPath !== "/storage"){
      const parentPath = currentPath.split("/").slice(0,-1).join("/") || "/storage";
      updatePath(parentPath);
    }
  }
  //메인 페이지 --------------------------------------------------------------------
>>>>>>> Stashed changes
  return (
    <div>
      <body>
        {/* 헤더 */}
        <header>
          <button className="logobtn">
            <img src="image/logo.png" className="logoimg" alt="logo"/>
            <h className="logofont">CLOUDBOX</h>
          </button>        
          <div className="searchbar">
            <img src="image/search.png" className="searchimg" alt="search"/>
            <input className="searchtext" placeholder="검색"></input>
          </div>
<<<<<<< Updated upstream
          <UserDropdown/>
          
=======
          <>
            {isDropdownView && <button className="userbtn" onClick={openSettingPopup}>계정관리</button>}
            {isDropdownView && <button className="userbtn" onClick={() => navigate('/')}>로그아웃</button>}
            <button className="userbtn" onClick={viewDropdown}>
              <img src={userInfo?.profileImage} onError={(e) => {e.target.onerror = null; 
                e.target.src = "image/user.png"}} className="headerimg" alt="user"/> 
              <h>{userInfo?.name}</h>
            </button>
          </>
>>>>>>> Stashed changes
        </header>

        {/* 메인 부분 */}
        <main>
          {/* 사이드 메뉴 */}
          <div className="side-container">
            <button className="side-menu" onClick={openFolderPopup}>
              <img src="image/add-folder.png" className="sideimg" alt="folder"/>새 폴더
            </button>
            <button className="side-menu">
<<<<<<< Updated upstream
              <img src="image/file-upload.png" className="sideimg" />파일 업로드
            </button>
            <div className="storage-viewer">
              <button className="storage-item">
                <img src="image/spread.png" className="sideimg" />
                <h >내 저장소</h>                 
              </button>
            </div>
            <button className="side-menu">
              <img src="image/trash.png" className="sideimg" />휴지통
            </button>
            <button className="side-menu">
              <img src="image/setting.png" className="sideimg" />설정
=======
              <img src="image/file-upload.png" className="sideimg" alt="file"/>
              <label htmlFor="file-upload" style={{ cursor: "pointer" }}>
                파일 업로드
              </label>
              <input
                id="file-upload"
                type="file"
                multiple
                style={{ display: "none" }}
                onChange={handleFileUpload}
              />
            </button>
            <div className="storage-viewer">
              <button className="storage-item">
                <img src={`image/spread.png`}  className="sideimg" alt="spread"/>
                <h >내 저장소</h>                 
              </button>
            </div>
            <button className="side-menu" >
              <img src="image/trash.png" className="sideimg" alt="bin"/>휴지통
            </button>
            <button className="side-menu" onClick={openSettingPopup}>
              <img src="image/setting.png" className="sideimg" alt="setting"/>설정
>>>>>>> Stashed changes
            </button>
          </div>
          {/* 메인 컨테이너 */}
          <div className="main-container">
            {/* 최근 파일 부분
            <div className="recent-container">최근 파일 </div> */}
            {/* 파일 부분 */}
            <div className="all-file-container">
              {/* 현재 표시되는 파일 경로 */}
              <h >{Path}</h>
              <table>
                <thead>
                  <tr>
                    <th></th>
                    <th onClick={() => handleSort("name")}>
                      파일 이름 {sortKey === "name" && (sortOrder === "asc" ? "▲" : "▼")}
                    </th>
                    <th onClick={() => handleSort("size")}>
                      파일 크기 {sortKey === "size" && (sortOrder === "asc" ? "▲" : "▼")}
                    </th>
                    <th>파일 유형</th>
                    <th onClick={() => handleSort("lastModified")}>
                      마지막 수정 날짜 {sortKey === "lastModified" && (sortOrder === "asc" ? "▲" : "▼")}
                    </th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
<<<<<<< Updated upstream
=======
                {currentPath !== "/storage" && (
                  <tr>
                  <td onDoubleClick={handleGoBack} className="back-btn" colSpan='6'>
                    /..
                    </td>
                  </tr>
                )}
>>>>>>> Stashed changes
                {sortedFiles.map((file, index) => (
                  <tr key={index}>
                    <td className="file-icon-column">
                      <img
                        src={`image/${file.type === "folder" ? "folder" : "file"}.png`}
                        alt={file.type}
                        className="file-icon"
                      />
                    </td>
                    <td>{file.name}</td>
                    <td>{file.type === "folder" ? "-" : `${(file.size / 1024).toFixed(2)} KB`}</td>
                    <td>{file.type}</td>
                    <td>
                      {file.type === "folder"
                        ? "-"
                        : new Date(file.lastModified).toLocaleString()}
                    </td>
                    <td className="menu-btn-column">
                      <button className="menu-btn">
                        <img src="image/menu.png" alt="Menu" />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
              </table>
            </div>
          </div>
        </main>

        {/* 새 폴더 팝업 */}
        {isFolderPopupOpen && (
          <div className="popup-overlay">
            <div className="popup">
              <h2>새 폴더 만들기</h2>
              <input
                type="text"
                placeholder="폴더 이름을 입력하세요"
                value={folderName}
                onChange={(e) => setFolderName(e.target.value)}
                className="popup-input"
              />
              <div className="popup-buttons">
                <button onClick={handleCreateFolder} className="popup-btn">
                  확인
                </button>
                <button onClick={closeFolderPopup} className="popup-btn">
                  취소
                </button>
              </div>
            </div>
          </div>
        )}
      </body>
    </div>
  );
}

export default MainPage;
