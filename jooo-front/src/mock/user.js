Mock.mock("/user/list?page=1", "get", {
    "code": 0,
    "totalCount": 100,
    "totalPage": 10,
    "pageNo": 1,
    "data": [{
        date: '2001-12-31',
        name: '张飞',
        address: '燕山当阳桥'
    }, {
        date: '2001-12-11',
        name: '关羽',
        address: '河东郡解县'
    }, {
        date: 'zzzzzzzz',
        name: '吕布',
        address: '并州五原郡九原县人'
    }, {
        date: '1984-02-13',
        name: '诸葛亮',
        address: '琅琊阳都'
    }],
    "msg": "success"
})

Mock.mock("/user/list?page=2", "get", {
    "code": 0,
    "totalCount": 100,
    "totalPage": 10,
    "pageNo": 1,
    "data": [{
        date: '2001-12-31',
        name: '张一飞',
        address: '燕山当阳桥'
    }, {
        date: '2001-12-11',
        name: '关两羽',
        address: '河东郡解县'
    }, {
        date: 'zzzzzzzz',
        name: '吕三布',
        address: '并州五原郡九原县人'
    }, {
        date: '1984-02-13',
        name: '诸葛四亮',
        address: '琅琊阳都'
    }],
    "msg": "success"
})