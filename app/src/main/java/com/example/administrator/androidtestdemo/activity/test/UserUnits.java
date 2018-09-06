package com.example.administrator.androidtestdemo.activity.test;

import android.util.Log;

import com.example.administrator.androidtestdemo.greedao.User;
import com.example.administrator.androidtestdemo.greedao.gen.UserDao;
import com.example.administrator.androidtestdemo.manager.GreenDaoManager;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class UserUnits {

    private void getuserById() {
        User user = getUserDao().load(1l);
        Log.i("tag", "结果：" + user.getId() + "," + user.getMemberSex() + "," + user.getMemberLastX() + ";");


    }

    public void insertdata(int memberSex,String  memberLastX) {
        //插入数据
        User insertData = new User();
        insertData.setId(null);
        insertData.setMemberSex(memberSex);
        insertData.setMemberLastX( memberLastX);
        getUserDao().insertOrReplace(insertData);
    }

    public void updatadata() {
        //更改数据
        List<User> userss = getUserDao().loadAll();
        User user = new User();
        user.setId(userss.get(0).getId());
        user.setMemberSex(1);
        user.setMemberLastX( "更改后的数据用户");
        getUserDao().update(user);

    }

    private UserDao getUserDao() {
        return GreenDaoManager.getInstance().getUserDao();
    }

    public void querydata() {
        //查询数据详细
        List<User> users = getUserDao().loadAll();
        Log.i("tag", "当前数量：" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getMemberSex() + "," + users.get(i).getMemberLastX()  + ";");
        }

    }

    public void querydataBy() {////查询条件
        Query<User> nQuery = getUserDao().queryBuilder()
//                .where(UserDao.Properties.Name.eq("user1"))//.where(UserDao.Properties.Id.notEq(999))
                .orderAsc(UserDao.Properties.Id)//.limit(5)//orderDesc
                .build();
        List<User> users = nQuery.list();
        Log.i("tag", "当前数量：" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getMemberSex() + "," + users.get(i).getMemberLastX()  + ";");
        }

//        QueryBuilder qb = userDao.queryBuilder();
//        qb.where(Properties.FirstName.eq("Joe"),
//                qb.or(Properties.YearOfBirth.gt(1970),
//                        qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
//        List youngJoes = qb.list();
    }


    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数
     * @return             数据列表
     */
    public List<User> queryN(String where, String... params){
        return getUserDao().queryRaw(where, params);
    }

    /**
     * 根据用户信息,插件或修改信息
     * @param user              用户信息
     * @return 插件或修改的用户id
     */
    public long saveN(User user){
        return getUserDao().insertOrReplace(user);
    }

    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        getUserDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User user = list.get(i);
                    getUserDao().insertOrReplace(user);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote(){
        getUserDao().deleteAll();
    }

    /**
     * 根据用户类,删除信息
     * @param user    用户信息类
     */
    public void deleteNote(User user){
        getUserDao().delete(user);
    }
}
