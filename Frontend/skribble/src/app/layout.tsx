import "./globals.css";

const layout = ({children}: Readonly<{children: React.ReactNode}>) => {
  return (
    <html lang="en">
      <head>
        <link rel="icon" href="/favicon.ico" sizes="any" />
      </head>
      <body>
        {children}
      </body>
    </html>
  );
}

export default layout