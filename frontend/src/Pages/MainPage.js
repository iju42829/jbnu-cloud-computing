import React, { useState } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import './MainPage.css'
 
  //로그인 창에서 넘어옴
  //사용자 정보(이메일, 이름, 프로필 사진(?)), 파일 저장소 정보, 옵션 정보를 받아옴
  //헤더의 사용자 부분을 사용자 정보로 초기화
  //메인 컨테이너의 내용을 파일 저장소 정보로 초기화
  //최근 파일의 최대 개수 제한
  //초기화 시'내 저장소'로 이동하도록 함
  //페이지 새로고침 시 다시 초기화하도록 함
  //사이드 컨테이너의 내 저장소 부분을 초기화
  //내 저장소의 구성을 확인할 수 있도록 함.
  //파일 제외, 폴더만 표시.
  //옵션 파일의 내용에 맞춰 사용자 설정 초기화(전체 뷰에 관여)
  //옵션이 변경될 경우 확인 버튼을 눌렀을 때 옵션 파일의 내용을 수정하고, 업데이트하는 형식


function MainPage() {

  //페이지 이동, 이전 페이지의 데이터 가져오기
  const navigate = useNavigate();  
  const location = useLocation();
  const {userInfo, storageInfo, options} = location.state || {};

  const [files, setFiles] = useState(storageInfo || []);


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

  //팝업창 관리
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

  return (
    <div>
      <body>
        {/* 헤더 */}
        <header>
          <button className="logobtn">
            <img src="image/logo.png" className="logoimg"/>
            <h className="logofont">CLOUDBOX</h>
          </button>        
          <div className="searchbar">
            <img src="image/search.png" className="searchimg" />
            <input className="searchtext" placeholder="검색"></input>
          </div>
          <UserDropdown/>
          
        </header>

        {/* 메인 부분 */}
        <main>
          {/* 사이드 메뉴 */}
          <div className="side-container">
            <button className="side-menu" onClick={openFolderPopup}>
              <img src="image/add-folder.png" className="sideimg" />새 폴더
            </button>
            <button className="side-menu">
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
