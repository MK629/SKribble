import { Toaster } from "react-hot-toast";
import "./globals.css";

const layout = ({children}: Readonly<{children: React.ReactNode}>) => {
  return (
    <html lang="en">
      <head>
        <link rel="icon" href="/favicon.ico" sizes="any" />
      </head>
      <body className="bg-gray-200">
        {children}
        <Toaster position="top-center" toastOptions={{style: {fontSize: "13px", padding: "8px 12px"}}}/>
      </body>
    </html>
  );
}

export default layout