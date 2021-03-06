//
// Copyright (c) 2012-2017 Red Hat, Inc.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//   Red Hat, Inc. - initial API and implementation
//

package jsonrpc

// NativeConn provides low level interface for jsonrpc.Tunnel
// to communicate with native connection such as websocket.
type NativeConn interface {

	// Write writes bytes to the connection.
	Write(body []byte) error

	// Next is blocking read of incoming messages.
	// If connection is closed an error of type *jsonrpc.CloseError
	// must be returned.
	Next() ([]byte, error)

	// Closes this connection.
	Close() error
}

// CloseError is an error which MUST be
// published by NativeConn implementations and used to determine
// the cases when tunnel job should be stopped.
type CloseError struct{ error }

// NewCloseError creates a new close error based on a given error.
func NewCloseError(err error) *CloseError {
	return &CloseError{error: err}
}
