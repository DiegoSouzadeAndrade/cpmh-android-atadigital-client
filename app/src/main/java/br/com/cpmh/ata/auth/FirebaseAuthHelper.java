package br.com.cpmh.ata.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import br.com.cpmh.ata.R;

/**
 *
 */
public class FirebaseAuthHelper
{
	private static final String TAG = "FirebaseAuthHelper";

	private FirebaseAuth auth;

	private FirebaseFirestore firestore;

	private OnFetchSignInMethodsForEmailListener onFetchSignInMethodsForEmailListener;

	private OnSendPasswordResetEmailListener onSendPasswordResetEmailListener;

	private OnUserIdTokenFetchListener onUserIdTokenFetchListener;

	private OnUserProfileFetchListener onUserProfileFetchListener;

	/**
	 *
	 */
	public FirebaseAuthHelper()
	{
		this.auth = FirebaseAuth.getInstance();
		this.firestore = FirebaseFirestore.getInstance();
	}

	/**
	 * @param onFetchSignInMethodsForEmailListener
	 */
	public void setOnFetchSignInMethodsForEmailListener(OnFetchSignInMethodsForEmailListener onFetchSignInMethodsForEmailListener)
	{
		if (onFetchSignInMethodsForEmailListener instanceof Context)
		{
			this.onFetchSignInMethodsForEmailListener = onFetchSignInMethodsForEmailListener;
		}
		else
		{
			throw new RuntimeException(onUserIdTokenFetchListener.toString().concat(" must extends android.content.Context."));
		}
	}

	/**
	 * @param onSendPasswordResetEmailListener
	 */
	public void setOnSendPasswordResetEmailListener(OnSendPasswordResetEmailListener onSendPasswordResetEmailListener)
	{
		if (onSendPasswordResetEmailListener instanceof Context)
		{
			this.onSendPasswordResetEmailListener = onSendPasswordResetEmailListener;
		}
		else
		{
			throw new RuntimeException(onUserIdTokenFetchListener.toString().concat(" must extends android.content.Context."));
		}
	}

	/**
	 * @param onUserIdTokenFetchListener
	 */
	public void setOnUserIdTokenFetchListener(OnUserIdTokenFetchListener onUserIdTokenFetchListener)
	{
		if (onUserIdTokenFetchListener instanceof Context)
		{
			this.onUserIdTokenFetchListener = onUserIdTokenFetchListener;
		}
		else
		{
			throw new RuntimeException(onUserIdTokenFetchListener.toString().concat(" must extends android.content.Context."));
		}
	}

	/**
	 * @param onUserProfileFetchListener
	 */
	public void setOnUserProfileFetchListener(OnUserProfileFetchListener onUserProfileFetchListener)
	{
		if (onUserProfileFetchListener instanceof Context)
		{
			this.onUserProfileFetchListener = onUserProfileFetchListener;

		}
		else
		{
			throw new RuntimeException(onUserIdTokenFetchListener.toString().concat(" must extends android.content.Context."));
		}
	}

	/**
	 *
	 */
	public void retrieveUserProfile(FirebaseUser user)
	{
		Activity activity = (Activity) onUserProfileFetchListener;
		firestore.collection(activity.getString(R.string.firestore_users_collection_path_key)).document(user.getUid()).addSnapshotListener(new UserProfileFetchListener());
	}

	/**
	 * @param email
	 */
	public void retrieveSignInMethodsForEmail(String email)
	{
		auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new FetchSignInMethodsForEmailListener());
	}

	/**
	 * @param email
	 */
	public void sendPasswordResetEmail(String email)
	{
		auth.sendPasswordResetEmail(email).addOnCompleteListener(new SendPasswordResetEmailListener());
	}

	/**
	 *
	 */
	public void displayLoginActivity()
	{
		Activity activity = (Activity) onUserIdTokenFetchListener;

		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 *
	 */
	public void validateUser(FirebaseUser user)
	{
		if (user != null)
		{
			user.getIdToken(true).addOnCompleteListener(new UserIdTokenFetchListener());
		}
		else
		{
			onUserIdTokenFetchListener.onInvalidUser();
		}
	}

	/**
	 *
	 */
	public interface OnUserIdTokenFetchListener
	{
		/**
		 *
		 */
		void onValidUser();

		/**
		 *
		 */
		void onInvalidUser();
	}

	/**
	 *
	 */
	public interface OnUserProfileFetchListener
	{
		/**
		 * @param userProfile
		 */
		void onRetrievedUserProfile(UserProfile userProfile);
	}

	/**
	 *
	 */
	public interface OnFetchSignInMethodsForEmailListener
	{
		/**
		 * @param exception
		 */
		void onFetchSignMethodsForEmailFailure(Exception exception);

		/**
		 * @param signInMethodsList
		 */
		void onFetchedSignMethodsForEmailSuccess(List<String> signInMethodsList);
	}

	/**
	 *
	 */
	public interface OnSendPasswordResetEmailListener
	{
		/**
		 *
		 */
		void onSendPasswordResetEmailSuccess();

		/**
		 * @param exception
		 */
		void onSendPasswordResetEmailFailure(Exception exception);
	}

	/**
	 *
	 */
	private class FetchSignInMethodsForEmailListener implements OnCompleteListener<SignInMethodQueryResult>
	{
		/**
		 * @param task
		 */
		@Override
		public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
		{
			if (task.isSuccessful())
			{
				Log.i(TAG, "Successfully fetched the sign in methods for email.");

				onFetchSignInMethodsForEmailListener.onFetchedSignMethodsForEmailSuccess(task.getResult().getSignInMethods());
			}
			else
			{
				Log.i(TAG, "Failed to fetch the sign in methods for email.");

				//

				//TODO: Implement a Crashlytics report here.
				onFetchSignInMethodsForEmailListener.onFetchSignMethodsForEmailFailure(task.getException());
			}
		}
	}

	/**
	 *
	 */
	private class SendPasswordResetEmailListener implements OnCompleteListener<Void>
	{
		/**
		 * @param task
		 */
		@Override
		public void onComplete(@NonNull Task<Void> task)
		{
			if (task.isSuccessful())
			{
				Log.i(TAG, "Successfully send password reset email.");

				onSendPasswordResetEmailListener.onSendPasswordResetEmailSuccess();
			}
			else
			{
				Log.w(TAG, "Failed to send password reset email.");

				//TODO: Implement a Crashlytics report here.

				onSendPasswordResetEmailListener.onSendPasswordResetEmailFailure(task.getException());
			}

		}
	}

	/**
	 *
	 */
	private class UserIdTokenFetchListener implements OnCompleteListener<GetTokenResult>
	{
		/**
		 * @param task
		 */
		@Override
		public void onComplete(@NonNull Task<GetTokenResult> task)
		{
			if (task.isSuccessful())
			{
				Log.i(TAG, "Authentication token is valid.");

				onUserIdTokenFetchListener.onValidUser();
			}
			else
			{
				try
				{
					throw Objects.requireNonNull(task.getException());
				}
				catch (Exception exception)
				{
					Log.w(TAG, "Authentication token is not valid.");

					// TODO: Implement a Crashlytics report here.
				}
				finally
				{
					onUserIdTokenFetchListener.onInvalidUser();
				}
			}
		}
	}

	/**
	 *
	 */
	private class UserProfileFetchListener implements EventListener<DocumentSnapshot>
	{
		/**
		 * @param snapshot
		 * @param exception
		 */
		@Override
		public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException exception)
		{
			UserProfile userProfile = null;

			if (exception != null)
			{
				Log.w(TAG, exception.getMessage());
			}
			else
			{
				userProfile = snapshot.toObject(UserProfile.class);
			}

			onUserProfileFetchListener.onRetrievedUserProfile(userProfile);
		}
	}
}
