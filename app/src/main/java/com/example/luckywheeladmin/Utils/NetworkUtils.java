package com.example.luckywheeladmin.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {

//  public static final String BASE_URL =  "http://192.168.1.103";
//  public static final String BASE_URL =  "http://192.168.1.41";
    public static final String BASE_URL =   "http://www.lucywheels.shop";

    public static final String BASE_API_FOLDER = "lucky_wheel_api/admin";
    public static final String GET_ALL_USERS = "get_all_users.php";
    public static final String DELETE_USER = "delete_user.php";
    public static final String GET_ALL_PARTICIPANTS = "get_all_participants.php";
    public static final String GET_ALL_CONTESTS = "get_all_contests.php";
    public static final String ADD_CONTEST = "add_contest.php";
    public static final String DELETE_CONTEST = "delete_contest.php";
    public static final String GET_ALL_WITHDRAWAL_REQUESTS = "get_all_withdrawal_requests.php";
    public static final String GET_ALL_WINNERS = "get_all_winners.php";
    public static final String CHANGE_USER_WINNER_STATE = "change_user_winner_state.php";
    public static final String CHANGE_REQUEST_STATE = "change_request_state.php";
    private static final String DELETE_REQUEST = "delete_request.php";
    private static final String DELETE_PARTICIPANT = "delete_participant.php";

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
    public static final String DELETE_REQUEST_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.DELETE_REQUEST;
    public static final String DELETE_PARTICIPANT_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.DELETE_PARTICIPANT;
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
    public static final String CHANGE_REQUEST_STATE_URL =
            NetworkUtils.BASE_URL + "/" +
                    NetworkUtils.BASE_API_FOLDER + "/" +
                    NetworkUtils.CHANGE_REQUEST_STATE;


    public static boolean checkInternetConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected());
    }
}
