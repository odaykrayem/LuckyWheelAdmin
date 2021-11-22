package com.example.luckywheeladmin.Utils;

public class NetworkUtils {
//    public static final String BASE_URL =  "http://192.168.43.130";
public static final String BASE_URL =  "http://192.168.1.107";

    public static final String BASE_API_FOLDER = "lucky_wheel_api/admin";
    public static final String GET_ALL_USERS = "get_all_users.php";
    public static final String DELETE_USER = "delete_user.php";
    public static final String GET_ALL_PARTICIPANTS = "get_all_participants.php";
    public static final String GET_ALL_CONTESTS = "get_all_contests.php";
    public static final String ADD_CONTEST = "add_contest.php";
    public static final String DELETE_CONTEST = "delete_contest.php";
    public static final String GET_ALL_WITHDRAWAL_REQUESTS = "get_all_withdrawal_requests.php";
    public static final String GET_ALL_WINNERS = "get_all_winners.php";
    public static final String CHANGE_USER_WINNER_STATE = "change_User_winner_state.php";
    //to do get delete all participants

    public static final String GET_ALL_USERS_URL =
                    NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    GET_ALL_USERS;

    public static final String DELETE_USER_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.DELETE_USER;

    public static final String GET_ALL_PARTICIPANTS_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.GET_ALL_PARTICIPANTS;

    public static final String GET_ALL_CONTESTS_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.GET_ALL_CONTESTS;

    public static final String ADD_CONTEST_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.ADD_CONTEST;

    public static final String DELETE_CONTEST_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.DELETE_CONTEST;

    public static final String GET_ALL_WITHDRAWAL_REQUESTS_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.GET_ALL_WITHDRAWAL_REQUESTS;

    public static final String GET_ALL_WINNERS_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.GET_ALL_WINNERS;

    public static final String CHANGE_USER_WINNER_STATE_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.CHANGE_USER_WINNER_STATE;


}
