import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class NotificationListener implements User32.MSGHOOKPROC {
    private User32 user32;
    private WinUser.HHOOK hook;

    public void startListening() {
        user32 = User32.INSTANCE;
        hook = user32.SetWindowsHookEx(User32.WH_SHELL, this, null, 0);
        if (hook == null) {
            System.err.println("Failed to install hook");
            return;
        }

        // Bucle principal para mantener la aplicación en ejecución
        WinUser.MSG msg = new WinUser.MSG();
        while (user32.GetMessage(msg, null, 0, 0) != 0) {
            user32.TranslateMessage(msg);
            user32.DispatchMessage(msg);
        }

        // Libera el gancho cuando la aplicación se detiene
        user32.UnhookWindowsHookEx(hook);
    }

    @Override
    public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
        if (nCode >= 0) {
            WinUser.CWPRETSTRUCT cwptr = new WinUser.CWPRETSTRUCT(Pointer.createConstant(lParam.longValue()));
            if (cwptr.message == User32.WM_SHELLHOOKMESSAGE && cwptr.wParam.intValue() == User32.HSHELL_FLASH) {
                // Se ha recibido una notificación de flash de ventana
                System.out.println("Se ha recibido una notificación de flash de ventana");
            }
        }
        return user32.CallNextHookEx(hook, nCode, wParam, lParam);
    }

    public static void main(String[] args) {
        NotificationListener listener = new NotificationListener();
        listener.startListening();
    }
}