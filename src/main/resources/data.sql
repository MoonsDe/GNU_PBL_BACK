-- 1. 캔 (ID=1 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (1, '캔', 'can', 
        '내용물을 비우고 물로 헹군 후 압착하여 배출합니다.', 
        '담배꽁초 등 이물질을 넣지 말아야 합니다.', 'assets/icons/can.png');

-- 2. 페트병 (ID=2 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (2, '플라스틱', 'plastic', 
        '내용물을 깨끗이 비우고 라벨을 제거한 후 찌그러뜨려 뚜껑을 닫아 배출합니다.', 
        '유색 페트병은 일반 플라스틱으로 분류해야 합니다.', 'assets/icons/pet.png');

-- 3. 유리병 (ID=3 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (3, '유리', 'glass', 
        '병뚜껑을 제거하고 내용물을 비운 뒤 물로 헹궈서 배출합니다.', 
        '거울, 전구, 깨진 유리는 일반쓰레기(불연성)로 배출해야 합니다.', 'assets/icons/glass.png');

-- 4. 종이팩 (ID=4 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (4, '종이팩', 'paperpack', 
        '내용물을 비우고 물로 헹군 뒤, 펼쳐서 말려 배출합니다.', 
        '일반 종이류와 섞이지 않게 종이팩 전용 수거함에 배출하는 것이 좋습니다.', 'assets/icons/paperpack.png');

-- 5. 비닐 (ID=5 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (5, '비닐류(과자봉지, 라면봉지)', 'vinyl', 
        '이물질을 깨끗이 씻어내고 투명 비닐봉투에 넣어 배출합니다.', 
        '음식물 등 이물질이 묻어 제거되지 않는 경우 종량제 봉투로 배출해야 합니다.', 'assets/icons/vinyl.png');

-- 6. 스티로폼 (ID=6 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (6, '스티로폼', 'styrofoam', 
        '테이프나 운송장 스티커를 완전히 제거하고 깨끗한 상태로 배출합니다.', 
        '색상이 있거나 코팅된 스티로폼은 재활용이 불가능하므로 종량제 봉투로 버려야 합니다.', 'assets/icons/styrofoam.png');

-- 7. 일반쓰레기 (ID=7 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (7, '일반쓰레기', 'general_waste', 
        '재활용이 불가능하므로 종량제 봉투에 담아 배출합니다.', 
        '깨진 유리, 도자기, 오염된 비닐 등은 일반쓰레기입니다.', 'assets/icons/trash.png');

-- 8. 알 수 없는 쓰레기 (ID=8 지정)
INSERT IGNORE INTO items (id, name, category, how_to_recycle, caution, image_url)
VALUES (8, '알수없음', 'unknown',        
        '죄송합니다! 제가 바보라 잘 모르겠습니다!', 
        '사진을 쓰레기가 잘 구분할 수 있게 찍어서 다시 보내주세요!', 'assets/icons/unknown.png');